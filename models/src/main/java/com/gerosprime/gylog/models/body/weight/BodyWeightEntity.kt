package com.gerosprime.gylog.models.body.weight

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class BodyWeightEntity (@PrimaryKey var recordId : Long,
                        var weight : Float, var dateLogged : Date,
                        var note : String)