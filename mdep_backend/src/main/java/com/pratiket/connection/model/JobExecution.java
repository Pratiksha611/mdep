package com.pratiket.connection.model;

import com.pratiket.connection.dto.ConnectionDTO;
import lombok.*;

import java.util.Map;

/**
 * @author Pratiksha Deshmukh
 * created on 21-09-2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobExecution
{
    private Map<String, ConnectionDTO> ConnectionDTOMap;
}
