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

import org.specs.runner.JUnit
import org.specs.Specification

class SqlTests extends Specification with JUnit {
    "Sql" should {
        
        "simple query as select/from/where clauses should be supported" in {
            val id: Field = "id"
            val table: Table = "table"
            var sql = select (id) from table where id > 2
            sql.toString must equalTo("select id from table where id>2")
            sql = select (id) from table where id < 2
            sql.toString must equalTo("select id from table where id<2")
            sql = select (id) from table where id <> 2
            sql.toString must equalTo("select id from table where id<>2")
        }
        
        "use '=?' instead of '=' in criteria as '=' is reserved by scala" in {
            val id: Field = "id"
            val table: Table = "table"
            var sql = select (id) from table where (id =? 2)
            sql.toString must equalTo("select id from table where id=2")            
        }
        
        "multiple fields should be able to query" in {
            val id: Field = "id"
            val name: Field = "name"
            val table : Table = "table"
            val sql = select (id, name) from table where id > 2
            sql.toString must equalTo("select id, name from table where id>2")
        }
        
        "and/or criterias should be support with round brackets" in {
            val id: Field = "id"
            val table : Table = "table"
            var sql = select (id) from table where ((id > 2) and (id < 3))
            sql.toString must equalTo("select id from table where (id>2 and id<3)")
            sql = select (id) from table where ((id > 6) or (id < 3))
            sql.toString must equalTo("select id from table where (id>6 or id<3)")
        }
        
        
    }
}