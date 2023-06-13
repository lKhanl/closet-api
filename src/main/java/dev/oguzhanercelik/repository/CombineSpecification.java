package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Combine;
import dev.oguzhanercelik.model.request.CombineFilterRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CombineSpecification {

    private static final Locale EN_LOCALE = Locale.forLanguageTag("en");

    public static Specification<Combine> getFilterQuery(Integer userId, CombineFilterRequest request) {
        return (root, query, cb) -> {
            Predicate conjunction = cb.conjunction();
            conjunction = cb.and(conjunction, cb.equal(root.get("userId"), userId));
            if (StringUtils.isNotBlank(request.getName())) {
                conjunction = cb.and(conjunction, cb.like(cb.lower(root.get("name")), likePattern(request.getName())));
            }
            return conjunction;
        };
    }

    private static String likePattern(String value) {
        return "%" + value.toLowerCase(EN_LOCALE) + "%";
    }
}