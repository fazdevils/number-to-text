package com.blackwaterpragmatic.numbertotext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.numbertotext.service.NumberToTextService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

	@Mock
	private NumberToTextService numberToTextService;

	@InjectMocks
	private Application application;

	@Test
	public void testConvertNumberToText() {
		final String arg0 = "0";
		final String expectedResult = "zero";

		when(numberToTextService.convert(arg0)).thenReturn(expectedResult);

		final String result = application.convertNumberToText(new String[]{arg0});

		verify(numberToTextService).convert(arg0);
		verifyNoMoreInteractions(numberToTextService);

		assertEquals(expectedResult, result);
	}

	@Test
	public void testFailureWhenNoArguments() {
		final String result = application.convertNumberToText(new String[]{});

		verifyNoMoreInteractions(numberToTextService);

		assertEquals("Usage: java -jar target/number-to-text.jar [number]", result);
	}

	@Test
	public void testFailureWhenTooManyArguments() {
		final String result = application.convertNumberToText(new String[]{"arg0", "arg1"});

		verifyNoMoreInteractions(numberToTextService);

		assertEquals("Usage: java -jar target/number-to-text.jar [number]", result);
	}

}
