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

class SQL (manipulation: String){
    var sql = new StringBuilder

    def from(table : Table):SQL={
        sql append " " append "from" append " " append table
        this
    }
    
    def where(criteria: Criteria): SQL={
        sql append " " append "where" append criteria
        this
    }

    override def toString() : String = sql.toString
}

class select (fields: Field*) extends SQL("select"){
    override def toString() : String = {
        var temp = new StringBuilder append "select" append " "
        for(field <- fields) temp append field append ","
        temp.deleteCharAt(temp.length - 1) append super.toString
        return temp.toString
    } 
}

object select{
    def apply (fields: Field*): select = new select (fields: _*)
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
    override def toString(): String = name
}

object Table{
    implicit def stringToTable(name: String): Table = new Table(name)
}
