package com.github.lybgeek.service.impl

import com.github.lybgeek.service.HelloService


class HelloServiceImpl implements HelloService {
    @Override
    String say(String username) {
        println ("hello:" + username)
        return "hello:" + username
    }
}
