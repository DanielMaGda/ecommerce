package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.response.AccountResponse;
import com.danmag.ecommerce.service.exceptions.InvalidArgumentException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {
    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private ModelMapper modelMapper;
    private AccountService accountService;


    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountsRepository, modelMapper);
    }


    @ParameterizedTest
    @MethodSource("provideAccountLists")
    void should_get_list_of_accounts(List<Account> accountList, List<AccountResponse> accountResponseList) {
        // given

        when(accountsRepository.findAll()).thenReturn(accountList);
        when(modelMapper.map(accountList.get(0), AccountResponse.class)).thenReturn(accountResponseList.get(0));
        when(modelMapper.map(accountList.get(1), AccountResponse.class)).thenReturn(accountResponseList.get(1));

        // when
        List<AccountResponse> returnedAccountResponses = accountService.getAllAccounts();

        // then
        assertEquals(2, returnedAccountResponses.size());
        assertEquals("John Doe", returnedAccountResponses.get(0).getUserName());
        assertEquals("Jane Doe", returnedAccountResponses.get(1).getUserName());
        verify(accountsRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(accountList.get(0), AccountResponse.class);
        verify(modelMapper, times(1)).map(accountList.get(1), AccountResponse.class);
    }


    @Test
    void should_get_account_by_id() {
        // given
        long id = 1L;
        Account account = Account.builder()
                .id(id)
                .userName("John Doe")
                .build();
        AccountResponse accountResponse = AccountResponse.builder()
                .id(id)
                .userName("John Doe")
                .build();
        when(accountsRepository.findById(id)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountResponse.class)).thenReturn(accountResponse);

        // when
        AccountResponse returnedAccountResponse = accountService.getAccountById(id);

        // then
        assertEquals(accountResponse, returnedAccountResponse);
        verify(accountsRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(account, AccountResponse.class);
    }

    @Test
    void should_throw_exception_when_account_not_present() {
        // given
        long id = 1L;
        when(accountsRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThrows(NoSuchElementException.class, () -> accountService.getAccountById(id));
        verify(accountsRepository, times(1)).findById(id);
        verify(modelMapper, never()).map(any(), eq(AccountResponse.class));
    }

    @Test
    void should_delete_account() {
        //given
        long id = 1L;
        Account account = Account.builder()
                .id(id)
                .build();
        when(accountsRepository.findById(id)).thenReturn(Optional.of(account));
        //when
        accountService.deleteAccount(account.getId());

        //then

        verify(accountsRepository).delete(account);
    }

    @Test
    void should_throw_exception_when_deleting_non_existing_account() {
        //given
        long nonExistingId = 123L;
        when(accountsRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        //when then
        assertThrows(NoSuchElementException.class, () -> accountService.deleteAccount(nonExistingId));
        verify(accountsRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void should_save_valid_account() {
        //given
        Account validAccount = Account.builder()
                .id(1)
                .userName("john")
                .build();
        when(accountsRepository.save(validAccount)).thenReturn(validAccount);

        //when
        Account savedAccount = accountService.saveAccount(validAccount);

        //then
        assertEquals(validAccount, savedAccount);
        verify(accountsRepository, times(1)).save(validAccount);
    }

    @Test
    void should_throw_exception_when_account_is_null() {
        // given
        Account emptyAccount = null;

        // Act and Assert
        assertThrows(InvalidArgumentException.class, () -> {
            accountService.saveAccount(emptyAccount);
        });

        // Verify
        verify(accountsRepository, never()).save(any(Account.class));
    }

    private static Stream<Arguments> provideAccountLists() {
        List<Account> accountList = Arrays.asList(
                Account.builder()
                        .id(1L)
                        .userName("John Doe")
                        .build(),
                Account.builder()
                        .id(2L)
                        .userName("Jane Doe")
                        .build()
        );
        List<AccountResponse> accountResponseList = Arrays.asList(
                AccountResponse.builder()
                        .id(1L)
                        .userName("John Doe")
                        .build(),
                AccountResponse.builder()
                        .id(2L)
                        .userName("Jane Doe")
                        .build()
        );
        return Stream.of(
                Arguments.of(accountList, accountResponseList)
        );
    }

}