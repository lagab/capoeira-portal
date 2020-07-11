package com.lagab.capoeira.capoeiraportal.service;

import com.lagab.capoeira.capoeiraportal.service.filter.Filter;
import com.lagab.capoeira.capoeiraportal.service.filter.RangeFilter;
import com.lagab.capoeira.capoeiraportal.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

@Transactional(
        readOnly = true
)
public abstract class QueryService<ENTITY> {
    public QueryService() {
    }

    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(field);
        });
    }

    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn(metaclassFunction, filter.getIn());
        } else if (filter.getNotIn() != null) {
            return this.valueNotIn(metaclassFunction, filter.getNotIn());
        } else if (filter.getNotEquals() != null) {
            return this.notEqualsSpecification(metaclassFunction, filter.getNotEquals());
        } else {
            return filter.getSpecified() != null ? this.byFieldSpecified(metaclassFunction, filter.getSpecified()) : null;
        }
    }

    protected Specification<ENTITY> buildStringSpecification(StringFilter filter, SingularAttribute<? super ENTITY, String> field) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(field);
        });
    }

    protected Specification<ENTITY> buildSpecification(StringFilter filter, Function<Root<ENTITY>, Expression<String>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(metaclassFunction, (String)filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn(metaclassFunction, filter.getIn());
        } else if (filter.getNotIn() != null) {
            return this.valueNotIn(metaclassFunction, filter.getNotIn());
        } else if (filter.getContains() != null) {
            return this.likeUpperSpecification(metaclassFunction, filter.getContains());
        } else if (filter.getDoesNotContain() != null) {
            return this.doesNotContainSpecification(metaclassFunction, filter.getDoesNotContain());
        } else if (filter.getNotEquals() != null) {
            return this.notEqualsSpecification(metaclassFunction, (String)filter.getNotEquals());
        } else {
            return filter.getSpecified() != null ? this.byFieldSpecified(metaclassFunction, filter.getSpecified()) : null;
        }
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> buildRangeSpecification(RangeFilter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(field);
        });
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> buildSpecification(RangeFilter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn(metaclassFunction, filter.getIn());
        } else {
            Specification<ENTITY> result = Specification.where((Specification)null);
            if (filter.getSpecified() != null) {
                result = result.and(this.byFieldSpecified(metaclassFunction, filter.getSpecified()));
            }

            if (filter.getNotEquals() != null) {
                result = result.and(this.notEqualsSpecification(metaclassFunction, filter.getNotEquals()));
            }

            if (filter.getNotIn() != null) {
                result = result.and(this.valueNotIn(metaclassFunction, filter.getNotIn()));
            }

            if (filter.getGreaterThan() != null) {
                result = result.and(this.greaterThan(metaclassFunction, filter.getGreaterThan()));
            }

            if (filter.getGreaterThanOrEqual() != null) {
                result = result.and(this.greaterThanOrEqualTo(metaclassFunction, filter.getGreaterThanOrEqual()));
            }

            if (filter.getLessThan() != null) {
                result = result.and(this.lessThan(metaclassFunction, filter.getLessThan()));
            }

            if (filter.getLessThanOrEqual() != null) {
                result = result.and(this.lessThanOrEqualTo(metaclassFunction, filter.getLessThanOrEqual()));
            }

            return result;
        }
    }

    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter, SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<? super OTHER, X> valueField) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(reference).get(valueField);
        });
    }

    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter, SetAttribute<ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField) {
        return this.buildReferringEntitySpecification(filter, (root) -> {
            return root.join(reference);
        }, (entity) -> {
            return entity.get(valueField);
        });
    }

    protected <OTHER, MISC, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter, Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity, Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(functionToEntity.andThen(entityToColumn), filter.getEquals());
        } else {
            return filter.getSpecified() != null ? this.byFieldSpecified((root) -> {
                return (Expression)functionToEntity.apply(root);
            }, filter.getSpecified()) : null;
        }
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(RangeFilter<X> filter, SetAttribute<ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField) {
        return this.buildReferringEntitySpecification(filter, (root) -> {
            return root.join(reference);
        }, (entity) -> {
            return entity.get(valueField);
        });
    }

    protected <OTHER, MISC, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(RangeFilter<X> filter, Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity, Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {
        Function<Root<ENTITY>, Expression<X>> fused = functionToEntity.andThen(entityToColumn);
        if (filter.getEquals() != null) {
            return this.equalsSpecification(fused, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn(fused, filter.getIn());
        } else {
            Specification<ENTITY> result = Specification.where((Specification)null);
            if (filter.getSpecified() != null) {
                result = result.and(this.byFieldSpecified((root) -> {
                    return (Expression)functionToEntity.apply(root);
                }, filter.getSpecified()));
            }

            if (filter.getNotEquals() != null) {
                result = result.and(this.notEqualsSpecification(fused, filter.getNotEquals()));
            }

            if (filter.getNotIn() != null) {
                result = result.and(this.valueNotIn(fused, filter.getNotIn()));
            }

            if (filter.getGreaterThan() != null) {
                result = result.and(this.greaterThan(fused, filter.getGreaterThan()));
            }

            if (filter.getGreaterThanOrEqual() != null) {
                result = result.and(this.greaterThanOrEqualTo(fused, filter.getGreaterThanOrEqual()));
            }

            if (filter.getLessThan() != null) {
                result = result.and(this.lessThan(fused, filter.getLessThan()));
            }

            if (filter.getLessThanOrEqual() != null) {
                result = result.and(this.lessThanOrEqualTo(fused, filter.getLessThanOrEqual()));
            }

            return result;
        }
    }

    protected <X> Specification<ENTITY> equalsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.equal((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X> Specification<ENTITY> notEqualsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.not(builder.equal((Expression)metaclassFunction.apply(root), value));
        };
    }

    protected Specification<ENTITY> likeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> {
            return builder.like(builder.upper((Expression)metaclassFunction.apply(root)), this.wrapLikeQuery(value));
        };
    }

    protected Specification<ENTITY> doesNotContainSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> {
            return builder.not(builder.like(builder.upper((Expression)metaclassFunction.apply(root)), this.wrapLikeQuery(value)));
        };
    }

    protected <X> Specification<ENTITY> byFieldSpecified(Function<Root<ENTITY>, Expression<X>> metaclassFunction, boolean specified) {
        return specified ? (root, query, builder) -> {
            return builder.isNotNull((Expression)metaclassFunction.apply(root));
        } : (root, query, builder) -> {
            return builder.isNull((Expression)metaclassFunction.apply(root));
        };
    }

    protected <X> Specification<ENTITY> byFieldEmptiness(Function<Root<ENTITY>, Expression<Set<X>>> metaclassFunction, boolean specified) {
        return specified ? (root, query, builder) -> {
            return builder.isNotEmpty((Expression)metaclassFunction.apply(root));
        } : (root, query, builder) -> {
            return builder.isEmpty((Expression)metaclassFunction.apply(root));
        };
    }

    protected <X> Specification<ENTITY> valueIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction, Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in((Expression)metaclassFunction.apply(root));

            Object value;
            for(Iterator var6 = values.iterator(); var6.hasNext(); in = in.value((X) value)) {
                value = var6.next();
            }

            return in;
        };
    }

    protected <X> Specification<ENTITY> valueNotIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction, Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in((Expression)metaclassFunction.apply(root));

            Object value;
            for(Iterator<X> var6 = values.iterator(); var6.hasNext(); in = in.value((X) value)) {
                value = var6.next();
            }

            return builder.not(in);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.greaterThanOrEqualTo((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.greaterThan((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.lessThanOrEqualTo((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.lessThan((Expression)metaclassFunction.apply(root), value);
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }
}
