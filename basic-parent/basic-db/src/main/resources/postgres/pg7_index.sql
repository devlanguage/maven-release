create index index_name on table_name(field_name1, field_name2,······);
create index index_name on table_name using btree 
create index idx_t_hash_1 on t_hash using btree (info); 创建索引 idx_t_hash_1，在t_hash表中的info列中。
create index  tbl_bb_index  on  tbl_bb(id,name);