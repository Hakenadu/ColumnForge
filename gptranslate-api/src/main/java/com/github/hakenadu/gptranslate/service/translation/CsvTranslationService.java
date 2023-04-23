package com.github.hakenadu.gptranslate.service.translation;

import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hakenadu.gptranslate.model.CsvTranslationRequestData;
import com.github.hakenadu.gptranslate.model.TranslationRequest;
import com.github.hakenadu.gptranslate.model.TranslationRequestData;
import com.github.hakenadu.gptranslate.service.completion.CompletionService;

import reactor.core.publisher.Flux;

@Service
public class CsvTranslationService implements TranslationService {

	private static final CSVFormat INPUT_FORMAT = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(false).build();

	@Autowired
	private CompletionService chatCompletionService;

	@Override
	public Flux<String> streamTranslations(TranslationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTranslation(TranslationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsData(final TranslationRequestData translationRequestData) {
		return translationRequestData instanceof CsvTranslationRequestData;
	}
}
