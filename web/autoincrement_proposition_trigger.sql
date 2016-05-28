use bourdonroad;
delimiter //

create trigger autoincr_proposition before insert on proposition
for each row
begin
	declare lineCount int;
	select count(*) from proposition into lineCount;
	set lineCount = lineCount + 1;
	set NEW.ID_PROP = lineCount;
end
//
delimiter ;