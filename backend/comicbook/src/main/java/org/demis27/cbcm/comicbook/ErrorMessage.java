package org.demis27.cbcm.comicbook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ErrorMessage {

    String code;

    String message;
}
