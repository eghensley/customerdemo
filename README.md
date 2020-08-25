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
		+ composite: multiple keys in a document
		+ functional: manipulated value for a key (UPPER())
		+ partial: includes where condition for applying to index
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
    'firstName': None,
    'lastName': None,
    'joinDate': None,
    'status': None,
    'dob': None,
    'type': 'customer',
    'gender': None
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
SELECT f.name
FROM fights f 
WHERE f.date < '2020-08-15 00:00:00'```

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
SELECT f.name eventName, l.city eventCity
FROM fights f left join locations l on f.location = meta(l).id```

### Group By
+ GROUP BY works only on a group key or aggregate function
+ ```SQL
SELECT l.city,
       COUNT(META(f).id) AS `count`
FROM fights f LEFT
    JOIN locations l ON f.location = META(l).id
WHERE f.date < '2020-08-15 00:00:00'
GROUP BY l.city```

### Collections
+ Supports standard SQL collection operators
	+ ANY, EVERY, ARRAY, FIRST, EXISTS, IN, and WITHIN
