package com.auction.service;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.dto.request.AuctionFinishByFinishPriceRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionCharity;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionCharityRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void changeStatusToFinished(List<AuctionEvent> list) {
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
        }

        auctionWinnerRepository.saveAll(listOfWinners);
        auctionEventRepository.saveAll(list);
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
}
