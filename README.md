# customerdemo

** Table of Contents **

[TOCM]

[TOC]

# Couchbase
## Config
+ Couchbase: 6.0.1
+ Springboot: 2.1.1

## Use Case Requirements
+ Fast Iteration of Data Models
+ Heavy emphasis on JSON by client applications
+ Low latency/High throughput
+ High number of concurrent users
+ Diverse users (geographically & time)
+ Large amounts of data (TB's)
+ Deploy across multiple data centers

## Best Practices
+ Sizing
	+ Node type parity
		+ Data nodes MUST be same size
	+ Documents < 2MB each
+ Sharding
	+ Buckets automatically divides each bucket into 1024 "vBuckets"
		+ Applications do not interact with vBuckets directly, occurs behind the scenes
		+ To find a document, CRC32 hashing used to identify which vBucket is needed
	+ Keep documents which will be referenced together within the same Bucket
+ Indexing
	+ Human readable primary key  indexes
		+ Bucket
			+ Document Type
				+ Document Instance (unique id a concatenation of bucket & document type & unique id)
					+ Attribute:Value
	+ Use double colons (::)
	+ Create indexes on keys/conditions used for views
	+ Couchbase supports comprehensive indexing (as opposed to MongoDB)
		+ simple: single key in a document
			+ ```sql 
			CREATE INDEX document_type_idx
 			ON `customer-info`(type)
 			USING GSI;```
		+ composite: multiple keys in a document
			+ ```sql 
			CREATE INDEX customer_name_idx
			 ON `customer-info`(firstName, lastName)
			 WHERE type = 'customer'
			 USING GSI;```
		+ functional: manipulated value for a key (UPPER())
		+ partial: includes where condition for applying to index
			+ ```sql 
			CREATE INDEX customer_gender_idx
			 ON `customer-info`(gender)
			 WHERE type = 'customer'
			 USING GSI;```
		+ array: subquery of documents (DISTINCT())

## Model
### Customer Information Toy Model
#### Bucket: customer-info
#### Document Schemas
COUNTRY = {
    'name': str,
    'abbrev': str,
    'type': 'country'
}

STATE = {
    'name': str,
    'country': str (many->one, country unique id),
    'abbrev': str,
    'type': 'state'
}

CITY = {
    'name': str,
    'state': str (many->one, state unique id),
    'type': 'city'
}

CUSTOMER = {
    'firstName': str,
    'lastName': str,
    'joinDate': str,
    'status': str [bronze, silver, gold, platinum],
    'dob': str,
    'type': 'customer',
    'gender': str [male, female],
    'city': str (many->one, city unique id)
}

RELATIONSHIP = {
    'customers': [],
    'relationship': str,
    'type': 'relationship',
}

## Storage
+ Writes are async by default
+ JSON documents stored in "Buckets"
	+ No enforced schema for each document
		+ No "impedance mismatch"

## Retrieval

### N1QL
+ Extends SQL principles to JSON documents

### Where
+ Supports standard SQL 
	+ AND, OR, NOT
+ ```SQL
select ci.* 
	from `customer-info` ci 
	where ci.type = 'city'```

### Joins/Limitations
+ Supports ANSI Joins
	+ Inner (default)
		+ Both the left-hand side and right-hand side source objects of the ON clause attribute must be non-MISSING and value non-NULL.
	+ Left [Outer]
		+ For each joined object produced, only the left-hand source objects of the ON clause attribute must be non-MISSING and value non-NULL
	+ Right [Outer]
		+ For each joined object produced, only the right-hand source objects of the ON clause attribute must be non-MISSING and value non-NULL
		+ RIGHT OUTER JOIN is only supported when it's the only join in the query; or in a chain of joins, the RIGHT OUTER JOIN must be the first join in the chain
+ ```sql
select ci.*, s.* 
	from `customer-info` ci 
	inner join `customer-info` s 
		on ci.state = META(s).id 
	where ci.type = 'city'```

### Group By
+ GROUP BY works only on a group key or aggregate function
	+ ```SQL
	select s.name stateName, count(meta(c).id) customerCount
		from `customer-info` ci 
		inner join `customer-info` s 
			on ci.state = META(s).id 
		inner join `customer-info` c
			on c.city = META(ci).id
		where ci.type = 'city'
			and s.type = 'state'
			and c.type = 'customer'
		group by s.name```

### Collections
+ Supports standard SQL collection operators
	+ ANY
		+ ```sql
		select r.*
    	from `customer-info` r 
    		where r.type = 'relationship'
    		AND ANY cust IN customers SATISFIES cust = "customers::061-06-0661" END;```
	+ EVERY
	+ ARRAY
		+ ```sql
		select array cust for cust in customers END as customerRelationships
			from `customer-info` r 
			where r.type = 'relationship'
				AND ANY cust IN customers SATISFIES cust = "customers::061-06-0661" END;```
	+ FIRST
	+ EXISTS
	+ IN
	+ WITHIN