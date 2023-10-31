package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequestDto implements Serializable {

    private static final long serialVersionUID = -1980894540233447578L;

    private int idEvaluationCenter;

    private LocalDate meetingDate;

    private int idSchedule;

    private String name;

    @NotBlank(message = "{NotBlank}")
    private String lastName;

    private String motherLastName;

    private String curp;

    private int idGender;

    private String mail;

    private String phone;

    private int idNationality;

    private String postalCode;

    private int idState;

    private int idCity;

    private int idColony;

    private String street;

    private int noExt;

    private int noInt;
}
