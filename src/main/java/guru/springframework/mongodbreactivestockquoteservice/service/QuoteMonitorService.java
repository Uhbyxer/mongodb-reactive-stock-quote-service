package guru.springframework.mongodbreactivestockquoteservice.service;

import guru.springframework.mongodbreactivestockquoteservice.client.StockQuoteClient;
import guru.springframework.mongodbreactivestockquoteservice.domain.Quote;
import guru.springframework.mongodbreactivestockquoteservice.repositories.QuoteRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuoteMonitorService implements ApplicationListener<ContextRefreshedEvent>{

	private final StockQuoteClient stockQuoteClient;

	private final QuoteRepository quoteRepository;

	public QuoteMonitorService(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
		this.stockQuoteClient = stockQuoteClient;
		this.quoteRepository = quoteRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		stockQuoteClient.getQuoteStream().log("quote-monitor-service")
		.subscribe(quote -> {
			Mono<Quote> saved = quoteRepository.save(quote);
			System.out.println("saved: " + saved.block().getId());
		});
	}
}
