package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarTest {

    @ParameterizedTest
    @CsvSource(value = {"3,3", "0,0", "213,213"}, delimiter = ',')
    void carMoveTest(final int moveCount, final int expectedPosition) {
        Car car = new Car("dummy");

        for (int i = 0; i < moveCount; i++) {
            car.move();
        }

        assertThat(car.getPosition()).isEqualTo(expectedPosition);
    }
}