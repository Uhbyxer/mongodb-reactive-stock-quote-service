package guru.springframework.mongodbreactivestockquoteservice;

import guru.springframework.mongodbreactivestockquoteservice.client.StockQuoteClient;
import guru.springframework.mongodbreactivestockquoteservice.domain.Quote;
import guru.springframework.mongodbreactivestockquoteservice.repositories.QuoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Component
public class QuoteRunner implements CommandLineRunner {

	private final StockQuoteClient stockQuoteClient;

	private final QuoteRepository quoteRepository;

	public QuoteRunner(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
		this.stockQuoteClient = stockQuoteClient;
		this.quoteRepository = quoteRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
//		Flux<Quote> quoteStream = stockQuoteClient.getQuoteStream();
		//		quoteStream.subscribe(System.out::println);

		Flux<Quote> quoteFlux = quoteRepository.findWithTailableCursorBy();
		Disposable disposable = quoteFlux.subscribe(quote -> {
			System.out.println("================== quote Id: " + quote.getId());
		});
		//disposable.dispose();
	}
}
