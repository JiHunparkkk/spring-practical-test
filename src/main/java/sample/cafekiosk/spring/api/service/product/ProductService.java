package sample.cafekiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;


/**
 * readOnly = true : 읽기 전용 CRUD 에서 CUD 동작 X / only Read JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 * <p>
 * CQRS - Command(CUD) / Query, Command 형 행위보다 Read 라는 행위의 빈도수가 훨씬 높음 Command, Query 에 대한 Responsibility 를 Separate 하자.
 * 책임을 분리해서 서로 연관이 없게 하자. 그거에 대한 시작으로 Transactional 을 이용하자
 * <p>
 * DB 에 대한 분리 CUD 에 대한 DB, R 에 대한 DB 를 보통 나눈다. 그 때 좋다.
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    //동시성 이슈
    //UUID
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(product -> ProductResponse.of(product))
                .collect(Collectors.toList());
    }

//    private String createNextProductNumber() {
//        String latestProductNumber = productRepository.findLatestProductNumber();
//        if (latestProductNumber == null) {
//            return "001";
//        }
//        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
//        int nextProductNumberInt = latestProductNumberInt + 1;
//
//        //9 -> 009 10 -> 010
//        return String.format("%03d", nextProductNumberInt);
//    }
}
