package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    public void 빈_문자열() {
        int num = calculator.splitAndSum("");
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void 인자_없음() {
        int num = calculator.splitAndSum(null);
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void 인자_하나() {
        int num = calculator.splitAndSum("1");
        assertThat(num).isEqualTo(1);
    }

    @Test
    public void 인자_두개_콤마_구별자() {
        int num = calculator.splitAndSum("1,2");
        assertThat(num).isEqualTo(3);
    }

    @Test
    public void 인자_두개_콜론_구분() {
        int num = calculator.splitAndSum("1:2");
        assertThat(num).isEqualTo(3);
    }

    @Test
    public void 커스텀_구분자를_지정() {
        int result = calculator.splitAndSum("//;\n1;2;3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void 음수_전달시_예외_처리() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calculator.splitAndSum("-1,2");
        });

        assertEquals("음수는 불가합니다!", exception.getMessage());
    }
}