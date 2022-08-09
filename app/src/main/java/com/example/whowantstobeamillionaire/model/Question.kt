package com.example.whowantstobeamillionaire.model

data class Question(val question: String, val level: Int, val caseA:String, val caseB:String
                    , val caseC:String,val caseD:String,val trueCase:Int)