package etf.bg.diplomski.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
public class BaseClass {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
}
