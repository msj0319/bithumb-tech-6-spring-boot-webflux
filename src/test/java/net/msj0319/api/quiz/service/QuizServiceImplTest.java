package net.msj0319.api.quiz.service;

import net.msj0319.api.quiz.domain.Quiz;
import net.msj0319.api.quiz.repository.AttemptRepository;
import net.msj0319.api.quiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {
    private QuizServiceImpl quizService;
    @Mock GeneratorServiceImpl generatorService;
    @Mock UserRepository userRepository;
    @Mock AttemptRepository attemptRepository;
    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl(generatorService, userRepository, attemptRepository);
    }

    @DisplayName("50 * 30 = 1500")
    @Test
    void createQuiz() {
        given(generatorService.randomFactor()).willReturn(50, 30);
        Mono<Quiz> monoQuiz = quizService.createQuiz();
        Quiz quiz = monoQuiz.block();
        assertThat(quiz.getFactorA(), is(50));
        assertThat(quiz.getFactorB(), is(30));
        assertThat(quiz.getResult(), is(1500));
    }
}