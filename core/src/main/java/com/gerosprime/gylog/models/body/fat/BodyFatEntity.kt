package com.gerosprime.gylog.models.body.fat

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class BodyFatEntity (@PrimaryKey var recordId : Long?,
                        var fat : Float, var dateLogged : Date,
                        var note : String = "") {

}