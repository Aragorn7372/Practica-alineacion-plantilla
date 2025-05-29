package org.example.newteamultimateedition.alineacion.validador

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.validator.Validate
import java.time.LocalDate

class AlineacionValidate: Validate<Alineacion,AlineacionError> {
    override fun validator(item: Alineacion): Result<Alineacion, AlineacionError> {
        if (item.personalList.size!=18){
            return Err(AlineacionError.AlineacionInvalidoError("no pueden ser menos o mas de 18 personas"))
        }
        if (item.juegoDate.isBefore(LocalDate.now())){
            return Err(AlineacionError.AlineacionInvalidoError("no puedes asignar una alineacion para una fecha anterior"))
        }

        return Ok(item)
    }
}