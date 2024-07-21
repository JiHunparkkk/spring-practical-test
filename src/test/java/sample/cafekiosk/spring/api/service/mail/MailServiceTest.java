package sample.cafekiosk.spring.api.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class) // 클래스가 시작될 때 Mockito 사용해서 Mock 만들꺼야.
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        //given
//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);
//        when(mailSendHistoryRepository.save(any(MailSendHistory.class)));
//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(), anyString(), anyString(), anyString());
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);
        //when
        boolean result = mailService.sendMail("", "", "", "");

        //then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any());
    }
}