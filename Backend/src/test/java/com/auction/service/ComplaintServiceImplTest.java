package com.auction.service;

import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.enums.ComplaintStatus;
import com.auction.model.fixture.AuctionEventFixture;
import com.auction.model.fixture.UserFixture;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionEventComplaintAuditRepository;
import com.auction.repository.AuctionEventComplaintRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceImplTest {

  @Mock
  private AuctionEventComplaintRepository auctionEventComplaintRepository;
  @Mock
  private AuctionEventComplaintAuditRepository auctionEventComplaintAuditRepository;
  @Mock
  private AuctionEventService auctionEventService;
  @Mock
  private UserService userService;
  @Mock
  private Mapper<AuctionEventComplaint, ComplaintDto> complaintComplaintDtoMapper;
  @Mock
  private Mapper<AuctionEventComplaintAudit, ComplaintAuditDto> complaintAuditComplaintAuditDtoMapper;
  @Mock
  private ApplicationEventPublisher publisher;


  private ComplaintServiceImpl complaintService;
  private ComplaintRequest request;
  private AuctionEventComplaint complaint;
  private ComplaintDto complaintDto;

  @BeforeEach
  public void setUp() {
    complaintService = new ComplaintServiceImpl(auctionEventComplaintRepository,
                                                auctionEventComplaintAuditRepository,
                                                userService,
                                                publisher,
                                                auctionEventService,
                                                complaintComplaintDtoMapper,
                                                complaintAuditComplaintAuditDtoMapper);
    request = new ComplaintRequest();
    request.setUserId(2L);
    request.setMessage("Hello");
    request.setAuctionEventId(4L);

    complaint = AuctionEventComplaint.builder()
            .genDate(LocalDateTime.now())
            .auctionEvent(AuctionEventFixture.auctionEvent())
            .message(request.getMessage())
            .status(ComplaintStatus.WAITING)
            .user(UserFixture.user())
            .build();

    complaintDto = ComplaintDto.builder()
            .message(request.getMessage())
            .user(UserFixture.userDto())
            .id(1L)
            .status(complaint.getStatus())
            .genDate(complaint.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
            .auctionEvent(new AuctionEventDto())
            .build();
  }

  @Test
  void create_whenInvoked_returnNewComplaintDto() {
    when(userService.findById(any(Long.class))).thenReturn(UserFixture.user());
    when(auctionEventService.findById(any(Long.class))).thenReturn(AuctionEventFixture.auctionEvent());
    when(auctionEventComplaintRepository.save(any(AuctionEventComplaint.class))).thenReturn(complaint);
    when(complaintComplaintDtoMapper.map(any(AuctionEventComplaint.class))).thenReturn(complaintDto);

    assertThat(complaintService.create(request)).isNotNull();
  }
}
