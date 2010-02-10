/*
 * Copyright 2010 Ming Jin(skyairmj@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package sqlins

class Statement{
}

trait FromRequired{
    def from(table : Table) : WhereRequired
}

trait WhereRequired{
    def where(criteria : Criteria) : Statement
}

class select (fields: Field*) extends Statement with FromRequired with WhereRequired{
    var sql = new StringBuilder append "select"
    for(field <- fields) sql append " " append field append ","
    sql.deleteCharAt(sql.length - 1)
    
    def from(table: Table):WhereRequired = {
        sql append " " append "from" append table
        this
    }
    
    def where(criteria : Criteria):Statement = {
        sql append " " append "where" append criteria
        this
    }
    
    override def toString() : String = sql.toString
}

object select{
    def apply (fields: Field*): FromRequired = new select (fields: _*)
}

class Criteria(criteria: String){
    override def toString() : String = criteria
}

class Field(name: String){
    def >(value : Int): Criteria = {
        new Criteria(" " concat name concat ">" concat value.toString)
    }

    def <(value : Int): Criteria = {
        new Criteria(" " concat name concat "<" concat value.toString)
    }

    def :=(value : Int): Criteria = {
        new Criteria(" " concat name concat "=" concat value.toString)
    }

    def <>(value : Int): Criteria = {
        new Criteria(" " concat name concat "<>" concat value.toString)
    }

    override def toString() : String = name
}

object Field{
    implicit def stringToField(field: String): Field = new Field(field)
}

class Table(name : String){
    override def toString(): String = " ".concat(name)
}

object Table{
    implicit def stringToTable(name: String): Table = new Table(name)
}
