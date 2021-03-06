package com.blackwaterpragmatic.numbertotext;

import com.blackwaterpragmatic.numbertotext.service.NumberToTextService;
import com.blackwaterpragmatic.numbertotext.service.NumberToTextServiceViaNumbers;

public class Application {

	private final NumberToTextService numberToTextService;

	public Application(final NumberToTextService numberToTextService) {
		this.numberToTextService = numberToTextService;
	}

	public static void main(final String[] args) {
		final NumberToTextService numberToTextService = new NumberToTextServiceViaNumbers();
		final Application application = new Application(numberToTextService);
		final String result = application.convertNumberToText(args);
		System.out.println(result);
	}

	public String convertNumberToText(final String[] args) {
		if (1 != args.length) {
			return "Usage: java -jar target/number-to-text.jar [number]";
		}
		try {
			return numberToTextService.convert(args[0]);
		} catch (@SuppressWarnings("unused") final NumberFormatException e) {
			return String.format("ERROR: Number must be a whole number in the range of %s to %s",
					numberToTextService.minValue(),
					numberToTextService.maxValue());
		}

	}

}
