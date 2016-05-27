use bourdonroad;
delimiter //

create trigger autoincr_tentative before insert on tentative
for each row
begin
	declare lineCount int;
	select count(*) from tentative into lineCount;
	set lineCount = lineCount + 1;
	set NEW.ID_TENTATIVE = lineCount;
end
//
delimiter ;