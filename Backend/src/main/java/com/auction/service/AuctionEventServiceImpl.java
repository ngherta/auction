package com.auction.service;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.model.AuctionCharity;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import com.auction.repository.AuctionCharityRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserEntityRepository;
import com.auction.service.interfaces.AuctionEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionEventServiceImpl implements AuctionEventService {

    private final AuctionEventRepository auctionEventRepository;
    private final AuctionCharityRepository auctionCharityRepository;
    private final UserEntityRepository userEntityRepository;

    @Override
    @Transactional
    public AuctionEventDto save(AuctionEventRequest request) {
        Date nowDate = new Date();
        AuctionEvent auctionEvent = new AuctionEvent();
        auctionEvent.setTitle(request.getTitle());
        auctionEvent.setDescription(request.getDescription());

        User user = userEntityRepository.findById(request.getUserId()).get();
        auctionEvent.setUser(user);

        if (request.getAuctionStatus().equals(AuctionStatus.ACTIVE.name())) auctionEvent.setStatusType(AuctionStatus.ACTIVE);

        if (request.getAuctionType().equals(AuctionType.COMMERCIAL.name())) auctionEvent.setAuctionType(AuctionType.COMMERCIAL);


        auctionEvent.setGenDate(nowDate);

        auctionEvent = auctionEventRepository.save(auctionEvent);

        if (request.getCharityPercent() > 0) {
            AuctionCharity auctionCharity = new AuctionCharity();
            auctionCharity.setAuctionEvent(auctionEvent);
            auctionCharity.setPercent(request.getCharityPercent());
            auctionCharity = auctionCharityRepository.save(auctionCharity);
            return AuctionEventDto.from(auctionEvent, auctionCharity);
        }

        return AuctionEventDto.from(auctionEvent);
    }

    @Override
    @Transactional
    public void changeStatusToFinished(List<Long> list) {
        List<AuctionEvent> listOfEvents = new ArrayList<>();
        Optional<AuctionEvent> auctionEvent;

        for (Long id : list) {
            auctionEvent = auctionEventRepository.findById(id);
            if (auctionEvent.isPresent()) {
                auctionEvent.get().setStatusType(AuctionStatus.FINISHED);
                listOfEvents.add(auctionEvent.get());
            }
        }

        if (!listOfEvents.isEmpty()) {
            auctionEventRepository.saveAll(listOfEvents);
        }
    }
}
