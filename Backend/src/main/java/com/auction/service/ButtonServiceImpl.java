package com.auction.service;

import com.auction.exception.IoTException;
import com.auction.model.Button;
import com.auction.repository.ButtonRepository;
import com.auction.service.interfaces.AuctionActionService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.ButtonService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.CreateButtonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService {

  private final ButtonRepository buttonRepository;
  private final AuctionEventService auctionEventService;
  private final UserService userService;
  private final AuctionActionService auctionActionService;


  @Override
  @Transactional(readOnly = true)
  public Button findByButtonId(String buttonId) {
    return buttonRepository.findByButtonId(buttonId)
        .orElseThrow(() -> new IoTException("Button with id[" + buttonId + "] not found!"));
  }

  @Override
  @Transactional
  public void add(CreateButtonRequest request) {
    Button button = Button
        .builder()
        .buttonId(request.getButtonId())
        .connected(false)
        .build();
    buttonRepository.save(button);
  }

  @Override
  @Transactional
  public void connect(Long userId, Long auctionId, String buttonId) {
    Button button = findByButtonId(buttonId);
    button.setUser(userService.findById(userId));
    button.setCurrentAuctionEvent(auctionEventService.findById(auctionId));
    button.setConnected(true);
    buttonRepository.save(button);
  }

  @Override
  @Transactional
  public void disconnect(String buttonId) {
    Button button = findByButtonId(buttonId);
    button.setUser(null);
    button.setCurrentAuctionEvent(null);
    button.setConnected(false);
    buttonRepository.save(button);
  }

  @Override
  public AuctionActionDto defaultBet(String buttonId) {
    Button button = buttonRepository.findByButtonId(buttonId)
        .orElseThrow(() -> new IoTException("Button with id[" + buttonId + "] not found"));
    if (button.getCurrentAuctionEvent() == null) {
      throw new IoTException("Button[" + buttonId + "] is not connected to auction!");
    }
    return auctionActionService.defaultBet(button.getCurrentAuctionEvent(), button.getUser());
  }


}
