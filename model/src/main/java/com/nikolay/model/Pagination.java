package com.nikolay.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * The type Pagination.
 */
public class Pagination {

  private Integer offset;

  private Integer limit;

  /**
   * Instantiates a new Pagination.
   */
  public Pagination() {

  }

  /**
   * Instantiates a new Pagination.
   *
   * @param offset the offset
   * @param limit the limit
   */
  public Pagination(Integer offset, Integer limit) {
    this.offset = offset;
    this.limit = limit;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pagination that = (Pagination) o;
    return Objects.equals(offset, that.offset) &&
        Objects.equals(limit, that.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, limit);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Pagination.class.getSimpleName() + "[", "]")
        .add("offset=" + offset)
        .add("limit=" + limit)
        .toString();
  }
}
