package fpfinal.model

import cats._
import cats.implicits._

class DebtByPayer private (val debtByPerson: Map[Person, DebtByPayee]) {

  /** TODO: Get the debt summary by payee for this payer
    */
  def debtForPayer(person: Person): Option[DebtByPayee] = ???

  /** TODO: Get all the payers in a list
    */
  def allPayers(): List[Person] = ???
}

object DebtByPayer {
  def unsafeCreate(debtByPerson: Map[Person, DebtByPayee]): DebtByPayer =
    new DebtByPayer(debtByPerson)

  def fromExpense(expense: Expense): DebtByPayer =
    new DebtByPayer(Map(expense.payer -> DebtByPayee.fromExpense(expense)))

  implicit def eqDebtByPayer(
      implicit
      eqMap: Eq[Map[Person, DebtByPayee]]
    ): Eq[DebtByPayer] =
    Eq.instance((d1, d2) => d1.debtByPerson === d2.debtByPerson)

  /** TODO: Implement a monoid instance.
    *
    * Hint: Use the monoidMap instance and a suitable method to convert it
    * to the instance you need.
    */
  implicit def monoidDebtByPayer(
      implicit
      monoidMap: Monoid[Map[Person, DebtByPayee]]
    ): Monoid[DebtByPayer] = ???

  implicit def showDebtByPayer(
      implicit
      showPerson: Show[Person],
      showDebtByPayee: Show[DebtByPayee]
    ): Show[DebtByPayer] =
    Show.show { debtByPayer =>
      s"""Debt by payer:
       |
       |${debtByPayer
        .allPayers()
        .toNel
        .fold("  No debts found")(
          _.foldMap(payer =>
            s"${payer.show}:\n" + debtByPayer
              .debtForPayer(payer)
              .foldMap(_.show)
          )
        )}""".stripMargin
    }
}
