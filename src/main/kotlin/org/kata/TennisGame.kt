package org.kata

import java.lang.Math.abs

class Player(private val name: String, private var points: Int = 0) {
    fun scorePoint() {
        points += 1
    }

    fun scoredEnoughPointsToWin() = points >= 4
    fun scoredLessThan3Points() = points < 3

    fun isTiedWith(player2: Player) = points == player2.points

    fun score(): String {
        return when (points) {
            0 -> "Love"
            1 -> "Fifteen"
            2 -> "Thirty"
            else -> "Forty"
        }
    }

    fun scoreDelta(player2: Player) = points - player2.points
    fun hasMorePointsThan(player2: Player) = points > player2.points
    fun hasAdvantage(): String = "Advantage $name"
    fun hasWon(): String = "Win for $name"
}

class TennisGame(player1Name: String, player2Name: String) {

    private val onePoint = 1
    private val players = HashMap<String, Player>()
    private val player1: Player = Player(player1Name)
    private val player2: Player = Player(player2Name)

    init {
        players[player1Name] = player1
        players[player2Name] = player2
    }

    fun wonPoint(playerName: String) {
        players[playerName]?.scorePoint()
    }

    fun getScore(): String {
        return when {
            playersAreTied() -> tieScore()
            onePlayerHasEnoughPointsToWin() -> advantageOrEndGameScore()
            else -> regularScore()
        }
    }

    private fun onePlayerHasEnoughPointsToWin() = player1.scoredEnoughPointsToWin() || player2.scoredEnoughPointsToWin()

    private fun playersAreTied() = player1.isTiedWith(player2)

    private fun regularScore() = "${scoreOf(player1)}-${scoreOf(player2)}"

    private fun advantageOrEndGameScore(): String {
        return when (differenceOfScoreBetweenPlayers()) {
            onePoint -> playerThatLeads().hasAdvantage()
            else -> playerThatLeads().hasWon()
        }
    }

    private fun differenceOfScoreBetweenPlayers() = abs(player1.scoreDelta(player2))

    private fun playerThatLeads() = if (player1.hasMorePointsThan(player2)) player1 else player2

    private fun tieScore(): String {
        return if (player1.scoredLessThan3Points())
            "${scoreOf(player1)}-All"
        else
            "Deuce"
    }

    private fun scoreOf(player: Player) = player.score()

}