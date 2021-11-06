package com.auction.service;

import com.auction.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    public static void test() {
      List<String> list = new ArrayList<>();
      list.stream().flatMap();
    }
}
