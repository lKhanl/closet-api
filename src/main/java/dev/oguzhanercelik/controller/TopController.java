package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.entity.User;
import dev.oguzhanercelik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tops")
@RequiredArgsConstructor
public class TopController {

    private final UserService userService;

    @PostMapping
    public void createTop() {
        final User user = userService.find();
    }

}
