package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddressDto implements Serializable {

    private static final long serialVersionUID = 3406799623875957987L;

    private CatalogDto state;
    private CatalogDto city;
    private List<CatalogDto> colonies;
}
