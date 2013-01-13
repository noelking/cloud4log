create column family LogMessage with comparator = UTF8Type and subcomparator = UUIDType; 

update column family LogMessage with column_metadata =     
	        [
	        {column_name: errorMessage, validation_class: UTF8Type, index_type: KEYS},  
	        {column_name: stackTrace, validation_class: UTF8Type},
		{column_name: application, validation_class: UTF8Type, index_type: KEYS},  
		{column_name: host, validation_class: UTF8Type, index_type: KEYS}, 
		{column_name: level, validation_class: UTF8Type, index_type: KEYS}, 
		{column_name: environment, validation_class: UTF8Type, index_type: KEYS}, 
		{column_name: time, validation_class: UTF8Type, index_type: KEYS}, 
		{column_name: id, validation_class: UTF8Type, index_type: KEYS}, 
		{column_name: errorCode, validation_class: UTF8Type, index_type: KEYS},
	        {column_name: logType, validation_class: UTF8Type}
		];