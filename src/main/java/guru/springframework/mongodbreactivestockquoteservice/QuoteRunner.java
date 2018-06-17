package guru.springframework.mongodbreactivestockquoteservice;

import guru.springframework.mongodbreactivestockquoteservice.client.StockQuoteClient;
import guru.springframework.mongodbreactivestockquoteservice.domain.Quote;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class QuoteRunner implements CommandLineRunner {

	private final StockQuoteClient stockQuoteClient;

	public QuoteRunner(StockQuoteClient stockQuoteClient) {
		this.stockQuoteClient = stockQuoteClient;
	}

	@Override
	public void run(String... strings) throws Exception {
		Flux<Quote> quoteStream = stockQuoteClient.getQuoteStream();

		quoteStream.subscribe(System.out::println);
	}
}
