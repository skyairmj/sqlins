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

import org.specs.matcher._
import org.specs.runner.JUnit
import org.specs.Specification

class SqlTests extends Specification with JUnit {
    "pure sql in scala" should {
        
        case class equalToSQL(sql: String) extends BeEqualTo[Any](sql: String) {
            override def apply(v: => Any) = super.apply(v.toString)
        }
        
        "simple (*) query like 'select * from ...' is supported" in {
            val table : Table = "table"
            select (*) from table must equalToSQL("select * from table")
        }
        
        "scalar query with literal value is supported" in {
            select ("Happy Chinese New Year") must equalToSQL("select 'Happy Chinese New Year'")
        }
        
        "multiple fields is be able to query" in {
            val id: Field = "id"
            val name: Field = "name"
            val table : Table = "table"
            select (id, name) from table must equalToSQL("select id, name from table")
        }
        
        "single criteria in where clause is supported" in {
            val id: Field = "id"
            val table: Table = "table"
            select (id) from table where id > 2 must equalToSQL("select id from table where id>2")
            select (id) from table where id < 2 must equalToSQL("select id from table where id<2")
            select (id) from table where id <> 2 must equalToSQL("select id from table where id<>2")
        }
        
        "use '=?' rather than '=' as equal in criteria because '=' is reserved by scala" in {
            val id: Field = "id"
            val table: Table = "table"
            select (id) from table where (id =? 2) must equalToSQL("select id from table where id=2")            
        }
        
        "and/or criterias should be support with round brackets" in {
            val id: Field = "id"
            val table : Table = "table"
            val sql = select (id) from table where ((id > 2) and (id < 3))
            sql must equalToSQL("select id from table where (id>2 and id<3)")
            select (id) from table where ((id > 6) or (id < 3)) must equalToSQL("select id from table where (id>6 or id<3)")
        }
    }
}