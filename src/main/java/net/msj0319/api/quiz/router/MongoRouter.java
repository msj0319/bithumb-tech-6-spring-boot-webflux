package net.msj0319.api.quiz.router;

import net.msj0319.api.quiz.domain.User;
import net.msj0319.api.quiz.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class MongoRouter {
    private final UserRepository db;

    @Bean
    public RouterFunction<ServerResponse> findAll() {
        final RequestPredicate predicate = RequestPredicates
                .GET("/find")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        RouterFunction<ServerResponse> response = RouterFunctions.route(predicate, request -> {
            Flux<User> mapper = db.findAll();
            db.findRegexByAlias("happy-john").collectList().subscribe(System.out::println);
            Pageable page = PageRequest.of(0, 5);
            db.findByAliasWithPage("john", page).collectList().subscribe(System.out::println);

            User john = new User("john", "happy-john");
            db.insert(john).subscribe(System.out::println);

            User tom = new User("tom", "angry-john");
            db.insert(tom).subscribe(System.out::println);

            Mono<ServerResponse> res = ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromProducer(mapper, User.class));
            return res;
        });
        return response;
    }
}
