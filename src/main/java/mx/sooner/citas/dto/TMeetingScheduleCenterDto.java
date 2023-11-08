package mx.sooner.citas.dto;

import lombok.*;
import mx.sooner.citas.dto.CAttentionScheduleDto;
import mx.sooner.citas.dto.CEvaluationCenterDto;
import mx.sooner.citas.dto.CMeetingStatusDto;
import mx.sooner.citas.entity.TMeetingScheduleCenter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link TMeetingScheduleCenter}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TMeetingScheduleCenterDto implements Serializable {
    private static final long serialVersionUID = -443150628571808364L;
    @NotNull
    private CEvaluationCenterDto idEvaluationCenter;
    @NotNull
    private CAttentionScheduleDto idSchedule;
    @NotNull
    private LocalDate meetingDate;
    @NotNull
    private CMeetingStatusDto idMeetingStatus;
}