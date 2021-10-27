package com.auction.service;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
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
import com.auction.repository.UserEntityRepository;
import com.auction.service.interfaces.AuctionEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionEventServiceImpl implements AuctionEventService {

    private final AuctionEventRepository auctionEventRepository;
    private final AuctionCharityRepository auctionCharityRepository;
    private final UserEntityRepository userEntityRepository;
    private final AuctionWinnerRepository auctionWinnerRepository;
    private final AuctionActionRepository auctionActionRepository;

    @Override
    @Transactional
    public AuctionEventDto save(AuctionEventRequest request) {
        AuctionEvent auctionEvent = new AuctionEvent();
        auctionEvent.setTitle(request.getTitle());
        auctionEvent.setDescription(request.getDescription());

        User user = userEntityRepository.findById(request.getUserId()).get();
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
            return AuctionEventDto.from(auctionEvent, auctionCharity);
        }

        return AuctionEventDto.from(auctionEvent);
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
}
