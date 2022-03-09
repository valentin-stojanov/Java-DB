package com.example.demo.serrvices;

import java.math.BigDecimal;

public interface AccountService {
    void  withdrawMoney(BigDecimal amount, Long id);
    void transferMoney(BigDecimal anount, Long id);
}




