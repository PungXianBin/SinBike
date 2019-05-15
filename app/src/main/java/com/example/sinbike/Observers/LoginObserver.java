package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Account;

public interface LoginObserver {
    void loginSuccess(Account account);
    void loginFailed();
}
