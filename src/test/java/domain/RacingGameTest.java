package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RacingGameTest {

    @ParameterizedTest
    @MethodSource("parameterProvider")
    void getWinnersTest(final List<String> carNames, final List<Integer> intendedNumbers, final List<String> expectedWinners) {
        IntendedNumberGenerator intendedNumberGenerator = new IntendedNumberGenerator();
        RacingGame racingGame = new RacingGame(
                carNames,
                intendedNumberGenerator
        );
        intendedNumberGenerator.readRepository(intendedNumbers);

        for (int i = 0; i < intendedNumbers.size() / carNames.size(); i++) {
            racingGame.start();
        }

        assertEquals(
                racingGame.getWinners().stream().map(Car::getCarName).collect(Collectors.toList()), expectedWinners
        );
    }

    private Stream<Arguments> parameterProvider() {
        return Stream.of(
                Arguments.of(List.of("pobi", "crong"), List.of(5, 3, 9, 0, 0, 9), List.of("pobi")),
                Arguments.of(List.of("pobi", "crong"), List.of(3, 5, 9, 0, 0, 9), List.of("crong")),
                Arguments.of(List.of("pobi", "crong"), List.of(4, 4, 4, 4, 4, 4), List.of("pobi", "crong")),
                Arguments.of(List.of("pobi", "crong"), List.of(0, 0, 0, 0, 0, 0), List.of("pobi", "crong")),
                Arguments.of(List.of("pobi", "crong", "hadi"), List.of(3, 4, 9, 6, 7, 6, 0, 1, 2), List.of("crong", "hadi")),
                Arguments.of(List.of("pobi", "crong", "hadi"), List.of(7, 4, 9, 6, 7, 6, 0, 1, 2), List.of("pobi", "crong", "hadi"))
        );
    }

    class IntendedNumberGenerator implements NumberGenerator {

        private List<Integer> repository;
        private int index = 0;

        public void readRepository(final List<Integer> repository) {
            this.repository = repository;
        }

        @Override
        public int makeDigit() {
            return repository.get(index++);
        }
    }

    @Test
    void getCars를_통해_반환된_자동차_객체를_변형해도_내부의_상태는_영향을_받지_않는다() {
        Queue<Integer> nums = new LinkedList<>();
        RacingGame racingGame = new RacingGame(List.of("pobi", "crong"), nums::poll);
        racingGame.getCars().forEach(Car::move);

        racingGame.getCars().forEach((car) -> {
            assertThat(car.getPosition()).isEqualTo(0);
        });
    }

    @Test
    void getWinners를_통해_반환된_자동차_객체를_변형해도_내부의_상태는_영향을_받지_않는다() {
        Queue<Integer> nums = new LinkedList<>();
        RacingGame racingGame = new RacingGame(List.of("pobi", "crong"), nums::poll);
        racingGame.getWinners().forEach((Car::move));

        racingGame.getCars().forEach((car) -> {
            assertThat(car.getPosition()).isEqualTo(0);
        });
    }
}
