package com.gerosprime.gylog.models.exercises

interface SetEntity {

    var exerciseId: Long?
    var recordId : Long?
    var reps : Int?
    var weight: Float?
    var counterWeight: Float?
    var durationSeconds: Int
    var restTimeSeconds : Int
    var upToFailure :Boolean

}