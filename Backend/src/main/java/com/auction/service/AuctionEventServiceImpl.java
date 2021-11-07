package com.auction.service;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.dto.request.AuctionFinishByFinishPriceRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.web.model.AuctionAction;
import com.auction.web.model.AuctionCharity;
import com.auction.web.model.AuctionEvent;
import com.auction.web.model.AuctionWinner;
import com.auction.web.model.User;
import com.auction.web.model.enums.AuctionStatus;
import com.auction.web.model.enums.AuctionType;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionCharityRepository;
import com.auction.repository.AuctionEventImgRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionEventServiceImpl implements AuctionEventService {

    private final AuctionEventRepository auctionEventRepository;
    private final AuctionCharityRepository auctionCharityRepository;
    private final UserRepository userRepository;
    private final AuctionWinnerRepository auctionWinnerRepository;
    private final AuctionActionRepository auctionActionRepository;
    private final AuctionEventSortRepository auctionEventSortRepository;
    private final AuctionEventImgRepository auctionEventImgRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public AuctionEventDto save(AuctionEventRequest request) {
        AuctionEvent auctionEvent = new AuctionEvent();
        auctionEvent.setTitle(request.getTitle());
        auctionEvent.setDescription(request.getDescription());

        User user = userRepository.findById(request.getUserId()).get();
        auctionEvent.setUser(user);

        if (request.getAuctionStatus().equals(AuctionStatus.ACTIVE.name())) {
            auctionEvent.setStatusType(AuctionStatus.ACTIVE);
        }
        else if(request.getAuctionStatus().equals(AuctionStatus.EXPECTATION.name())) {
            auctionEvent.setStatusType(AuctionStatus.EXPECTATION);
        }

        auctionEvent.setGenDate(new Date());

        if (request.getCharityPercent() == 0) {
            auctionEvent = auctionEventRepository.save(auctionEvent);
            return AuctionEventDto.from(auctionEvent);
        }

        AuctionCharity auctionCharity = new AuctionCharity();
        if (request.getAuctionType().equals(AuctionType.COMMERCIAL.name())) {
            auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
        }
        else if (request.getCharityPercent() > 0) {
            auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
            auctionCharity.setAuctionEvent(auctionEvent);
            auctionCharity.setPercent(request.getCharityPercent());
            auctionCharity = auctionCharityRepository.save(auctionCharity);
        }

        return AuctionEventDto.from(auctionEvent, auctionCharity);
    }

    @Override
    @Transactional
    public void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException {
        List<AuctionWinner> listOfWinners = new ArrayList<>();

        for (AuctionEvent event : list) {
            AuctionWinner auctionWinner = new AuctionWinner();
            auctionWinner.setAuctionEvent(event);

            AuctionAction auctionAction = auctionActionRepository.getWinnerAuctionAction(event.getId());
            auctionWinner.setUser(auctionAction.getUser());
            auctionWinner.setPrice(auctionAction.getBet());

            listOfWinners.add(auctionWinner);
            log.info("User[" + auctionAction.getUser() + "] won auctionEvent[" + event.getId() + "]");

            event.setStatusType(AuctionStatus.FINISHED);
            log.info("AuctionEvent [" + event.getId() + "] set new status - FINISHED.");

            log.info("Start to send email to winner " + auctionWinner.getUser().getEmail());
            mailService.sendEmailToAuctionWinner(auctionWinner);
            log.info("Finish to send email to winner");

            log.info("Start to send email to participants " + event.getTitle());
            sendEmailToParticipants(event, auctionWinner);
            log.info("Finish to send email to participants");
        }

        auctionWinnerRepository.saveAll(listOfWinners);
        auctionEventRepository.saveAll(list);
    }

    @Override
    public void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException {
        List<AuctionAction> list = auctionActionRepository.getAllByAuctionGroupByUser(auctionEvent.getId(), auctionWinner.getUser().getId());
        if (!list.isEmpty()) {
            mailService.sendEmailToAuctionParticipant(list);
        }
    }

    @Override
    public AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent) {
        auctionEvent.setStatusType(AuctionStatus.BLOCKED);
        log.info("AuctionEvent[" + auctionEvent.getId() + "] new status - " + AuctionStatus.BLOCKED.name());
        return auctionEventRepository.save(auctionEvent);
    }

    @Override
    @Transactional
    public void finishByFinishPrice(AuctionFinishByFinishPriceRequest request) throws AuctionEventNotFoundException, UserNotFoundException {
        Optional<AuctionEvent> auctionEvent = auctionEventRepository.findById(request.getAuctionId());
        Optional<User> user = userRepository.findById(request.getUserId());

        if (!auctionEvent.isPresent()) {
            throw new AuctionEventNotFoundException("Auction event[" + request.getAuctionId() + "doesn't exist");
        }

        if (!user.isPresent()) {
            throw new UserNotFoundException("User[" + request.getUserId() + "] doesn't exist");
        }

        auctionEvent.get().setStatusType(AuctionStatus.FINISHED);
        auctionEventRepository.save(auctionEvent.get());


        AuctionWinner auctionWinner = new AuctionWinner();
        auctionWinner.setUser(user.get());
        auctionWinner.setAuctionEvent(auctionEvent.get());
        auctionWinner.setPrice(auctionEvent.get().getFinishPrice());
        auctionWinnerRepository.save(auctionWinner);
    }

    @Override
    public List<AuctionEventDto> getAllSortByRating() {
        List<AuctionEventDto> auctionEventDtoList = new ArrayList<>();
        List<AuctionEvent> auctionEventList = auctionEventRepository.getAuctionEventByRating();

        for (AuctionEvent auctionEvent : auctionEventList) {
            if (auctionEvent.getAuctionType().equals(AuctionType.CHARITY.name())) {
                AuctionCharity auctionCharity = auctionCharityRepository.findByAuctionEvent(auctionEvent.getId());
                auctionEventDtoList.add(AuctionEventDto.from(auctionEvent, auctionCharity));
            }
            else {
                auctionEventDtoList.add(AuctionEventDto.from(auctionEvent));
            }
        }

        return auctionEventDtoList;
    }

    @Override
    public List<AuctionEventDto> getAll() {
        List<AuctionEvent> list = auctionEventRepository.findAll();
        List<AuctionEventDto> auctionEventDtoList = new ArrayList<>();
        for (AuctionEvent auctionEvent : list) {
            if (auctionEvent.getAuctionType().equals(AuctionType.CHARITY.name())) {
                AuctionCharity auctionCharity = auctionCharityRepository.findByAuctionEvent(auctionEvent.getId());
                auctionEventDtoList.add(AuctionEventDto.from(auctionEvent, auctionCharity));
            }
            else {
                auctionEventDtoList.add(AuctionEventDto.from(auctionEvent));
            }
        }

        return auctionEventDtoList;
    }

    @Override
    @Transactional
    public void delete(AuctionEvent auctionEvent) {
        if (auctionEvent.getAuctionType().equals(AuctionType.CHARITY)) {
            AuctionCharity auctionCharity = auctionCharityRepository.findByAuctionEvent(auctionEvent.getId());
            auctionCharityRepository.delete(auctionCharity);
        }

        if (auctionEvent.getStatusType().equals(AuctionStatus.FINISHED)) {
            AuctionWinner auctionWinner = auctionWinnerRepository.findByAuctionEvent(auctionEvent.getId());
            auctionWinnerRepository.delete(auctionWinner);
        }

        auctionEventImgRepository.deleteAllByAuctionEvent(auctionEvent);

        auctionEventSortRepository.deleteAllByAuctionEvent(auctionEvent.getId());

        auctionActionRepository.deleteAllByAuctionEvent(auctionEvent);
    }

    @Override
    @Transactional
    public AuctionEventDto update(AuctionEventRequest request, Long auctionId) throws AuctionEventNotFoundException {
        Optional<AuctionEvent> auctionEvent = auctionEventRepository.findById(auctionId);
        if (!auctionEvent.isPresent()) {
            throw new AuctionEventNotFoundException("Auction event[" + auctionId + "] doesn't exist.");
        }

        ///Need to add more exceptions!
        AuctionType oldAuctionType = auctionEvent.get().getAuctionType();

        auctionEvent.get().setId(auctionId);
        auctionEvent.get().setTitle(request.getTitle());
        auctionEvent.get().setDescription(request.getDescription());

        ///

        if (request.getAuctionStatus().equals(AuctionStatus.ACTIVE.name())) {
            auctionEvent.get().setStatusType(AuctionStatus.ACTIVE);
        }
        else if(request.getAuctionStatus().equals(AuctionStatus.EXPECTATION.name())) {
            auctionEvent.get().setStatusType(AuctionStatus.EXPECTATION);
        }

        if (request.getCharityPercent() == 0) {
            auctionEvent = Optional.ofNullable(auctionEventRepository.save(auctionEvent.get()));
            return AuctionEventDto.from(auctionEvent.get());
        }

        AuctionCharity auctionCharity = new AuctionCharity();

        if (oldAuctionType.name().equals(AuctionType.CHARITY.name()) && !request.getAuctionType().equals(AuctionType.CHARITY.name())) {
            auctionCharity = auctionCharityRepository.findByAuctionEvent(auctionId);
            auctionCharityRepository.delete(auctionCharity);
            auctionEvent.get().setAuctionType(AuctionType.COMMERCIAL);
        }
        else if (request.getCharityPercent() > 0 && auctionEvent.get().getAuctionType().equals(AuctionType.CHARITY.name())){
            auctionCharity = auctionCharityRepository.findByAuctionEvent(auctionId);
            auctionCharity.setPercent(request.getCharityPercent());
            auctionCharityRepository.save(auctionCharity);
        }
        else if (request.getCharityPercent() > 0 && auctionEvent.get().getAuctionType().equals(AuctionType.COMMERCIAL.name())){
            auctionCharity = new AuctionCharity();
            auctionCharity.setPercent(request.getCharityPercent());
            auctionCharity.setAuctionEvent(auctionEvent.get());
            auctionCharityRepository.save(auctionCharity);
        }

        return AuctionEventDto.from(auctionEvent.get(), auctionCharity);
    }


}
