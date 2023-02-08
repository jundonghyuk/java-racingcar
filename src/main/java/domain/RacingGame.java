package domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RacingGame {

    private final List<Car> cars;

    private final int MOVABLE_BOUND = 4;

    public RacingGame(List<String> splitCarNames) {
        cars = splitCarNames.stream()
                .map(Car::new)
                .collect(Collectors.toList());
    }

    public void start() {
        for (Car car : cars) {
            moveCar(car);
        }
    }

    private void moveCar(Car car) {
        int randomNumber = RandomNumberGenerator.makeRandomDigit();
        if (randomNumber >= MOVABLE_BOUND) {
            car.move();
        }
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }



    public List<Car> getWinners() {
        Car furthestCar = getFurthestCar();

        return cars.stream()
                .filter(car -> car.getPosition() == furthestCar.getPosition())
                .collect(Collectors.toList());
    }

    private Car getFurthestCar() {
        return cars.stream()
                .max(Comparator.comparingInt(Car::getPosition))
                .get();
    }
}