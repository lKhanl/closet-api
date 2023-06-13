package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Bottom;
import dev.oguzhanercelik.model.request.BottomFilterRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BottomSpecification {

    private static final Locale EN_LOCALE = Locale.forLanguageTag("en");

    public static Specification<Bottom> getFilterQuery(Integer userId, BottomFilterRequest request) {
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