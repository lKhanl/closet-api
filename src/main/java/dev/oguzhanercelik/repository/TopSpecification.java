package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Top;
import dev.oguzhanercelik.model.request.TopFilterRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TopSpecification {

    private static final Locale EN_LOCALE = Locale.forLanguageTag("en");

    public static Specification<Top> getFilterQuery(Integer userId, TopFilterRequest request) {
        return (root, query, cb) -> {
            Predicate conjunction = cb.conjunction();
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