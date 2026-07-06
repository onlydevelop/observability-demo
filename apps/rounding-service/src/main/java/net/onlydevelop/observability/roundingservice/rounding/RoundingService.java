package net.onlydevelop.observability.roundingservice.rounding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class RoundingService {

	public double round(double value, int roundedUpto) {
		return BigDecimal.valueOf(value)
				.setScale(roundedUpto, RoundingMode.HALF_UP)
				.doubleValue();
	}

}
