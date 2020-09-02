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
	+ All index creation is done asynchronously
		+ GSI (global secondary index)
		+ ```CREATE [PRIMARY] INDEX```
		+ Uses B+ tree
		+ Performance Benefits:
			+ Advanced Scaling: GSIs can be assigned independently to selected nodes
			+ Predictable Performance: Key-based operations maintain predictable low-latency
			+ Low Latency Querying: GSIs independently partition into the Index Service nodes
			+ Independent Partitioning: Data and its indexes can have different partition keys
+ Views
	+ In addition to Indexing, views are also supported
	+ user defined map and reduce function that can define arbitrarily complex logic for indexing
	+ Map function can call the `emit()` function to generate an index item for the mutation being processed
	+ Comparable to SQL "Materialized Views"
		+ Query / Indexing is cached to allow quick retrieval
		+ Also means that the views are updated with any update to the design document
		+ Stored on disk, trade memory for performance
	+ Supports "Spatial Views"
		+ Use "R-tree" for geographic data
	+ Performance Advantages:
		+ Auto Partitioning of Indexes: Views come with auto partitioning and smart placement within the data service
		+ Built-in replicas and HA for Indexing: Views come with built in replicas for high availability within the data service
		+ Simple Scaling Model: Views are automatically placed and scaled with the data service
	+ Best Practices
		+ View quantity per design document
			+ Ideally 1 view per design document, to avoid unnecessary updates
		+ Modifying existing views
			+ Modification requires rebuilding the entire view, so is very costly
		+ Dont include document IDs
			+ Automatically included from `META()`
		+ Check document fields
			+ First ensure the field exists before performing operation
			+ ```
			function(doc, meta)
				{
				  if (doc.ingredient)
				  {
				     emit(doc.ingredient.ingredtext, null);
				  }
				}```
		+ View Size, disk storage and I/O
			+ Size expands linearly with number of views
			+ (% of docment viewed) x (number of views) x (size of document)
		+ Include value data in views
			+ Only include the minimum data required for querying
		+ Use document types
			+ Preemptively filter documents by type
		+ Use built-in Reduce functions
			+ highly optimized
				+ `_sum`
				+ `_count`


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
	
	
## Code Structure/Best Practices

### Organization

+ App.java
+ dao
	+ Foo.java
	+ Bar.java
+ repository
	+ FooRepository.java
	+ BarRepository.java
+ service
	+ FooService.java
	+ BarService.java
+ rest
	+ FooController.java
	+ BarController.java
	
#### App.java
+ Main class which launches the application
+ Annoted with `@SpringBootApplication`

#### dao
+ One DAO class per design document/view
+ Annoted with `@Document`
+ Requires both full and partial constructors
+ Include `toString`, `equals`, and `hashCode` methods
	+ Annotated with `@Override`
+ One private method per JSON field
	+ Annotated with `@Field`

#### repository
+ One interface per DAO
+ Extend `CrudRepository<{Dao}, String>`
+ Define each query to be performed
	+ Use N1QL notation

#### service
+ One class per repository
+ Annotate with `@Service`
+ `private final {repository}`
+ Use a full constructor to inject dependency repository
	+ Annotated with `@Autowired`
+ Wrap each method in try/catch

#### rest
+ One class per service
+ Annotate with `@RestController`, `@RequestMapping({pathPrefix})`, and `@Api(value={swaggerDescription})`
+ Inject service dependency with `@Autowired`
+ One method per HTTP endpoint
	+ Annotated with `@ApiOperation(value = {swaggerDefinition})`
	+ Type of call defined with `@GetMapping({path})`, `@PostMapping({path})`, etc
	+ Wrap each in try/catch
	+ Define path variables with `@PathVariable`
	+ Define body variables with `@RequestBody`
	+ Define headers with `@RequestHeader`
	
### Testing

+ At LEAST one unit test per method, per class
+ One unit test per "branch" in a method's logic
	+ ex) try/catch requires 2 unit tests: one for success one for caught error
+ JUnit for DAO class
+ Mockito for repository/service/controller
+ Use ```@RunWith(MockitoJUnitRunner.class)
@SpringBootTest``` for DAO, repository, and service
+ Use ```@RunWith(SpringRunner.class)
@WebMvcTest(value = {Controller}.class)``` for controller


## Kafka

### Configurations

#### General
+ brokers: List of broker hosts to connect
	+ optionally include port
	+ default: localhost
+ defaultBrokerPort: default port to connect to
	+ default: 9092
+ offsetUpdateTimeWindow: frequency (miliseconds) that offsets are saved
	+ default: 10,000
+ offsetUpdateCount: number of updates that offsets are saved
	+ mutually exclusive with `offsetUpdateTimeWindow`
	+ default: 0
	

#### Consumer
+ autoRebalanceEnabled: if partitions are balanced between consumers.  If not, each consumer assigned a fixed set
	+ default: True
+ autoCommitOffset: whether commits occur automatically, or must be sent through explicit acknowledgement header
	+ default: True
+ autoCommitOnError: still commit the offset in event of an error
	+ default: null
+ recoveryInterval: interval (milliseconds) for connection recovery attempts
	+ default: 5000
+ startOffset: starting point for new consumers
	+ available options:
		+ earliest: start from the beginning
		+ latest: start from most recent
	+ default: earliest
+ enableDlq (dead letter queue): sends error messages to `error.<destination>.<group>`
	+ default: false

#### Producer
+ bufferSize: upper limit for data (bytes) that can be sent in a single batch
	+ default: 16384
+ sync: if producer is synchonous
	+ default: false
+ batchTimeout: how long the producer will wait between sending for larger batches
	+ throughput vs latency
	+ default: 0